package com.colorpalettegenerator.web;

import com.colorpalettegenerator.model.Pixel;
import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileUploadController {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
    public static final int NUM_CLUSTERS=4;

    @PostMapping("/upload")
    public String handleFileUpload(@RequestPart("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            // Handle empty file
            model.addAttribute("errorMessage", "Please select a file to upload.");
            return "upload-image";
        }

        try {
            // Log image path for debugging
            String imagePath = "/uploads/" + file.getOriginalFilename();
            System.out.println("Image Path: " + imagePath);

            // Save the file to the server
            Path uploadPath = Path.of(UPLOAD_DIR);
            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Read pixel values from the resized image
            BufferedImage originalImage = ImageIO.read(new File(UPLOAD_DIR + file.getOriginalFilename()));
            int targetWidth = 100; // Set your desired width
            int targetHeight = 100; // Set your desired height
            List<Pixel> pixels = readPixels(originalImage, targetWidth, targetHeight);

            // Apply k-means clustering
            List<Pixel> clusteredPixels = kMeansClustering(pixels, NUM_CLUSTERS);

            // Log clustered colors
            System.out.println("Clustered Colors: " + clusteredPixels);

            // Set the uploaded image path and clustered colors for display
            model.addAttribute("uploadedImagePath", imagePath);
            model.addAttribute("clusteredColors", clusteredPixels);
        } catch (IOException e) {
            // Handle file upload error
            model.addAttribute("errorMessage", "Error uploading the file.");
            return "upload-image";
        }

        return "upload-image";
    }
    private List<Pixel> readPixels(BufferedImage originalImage, int targetWidth, int targetHeight) {
        // Resize the original image
        Image tmp = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        List<Pixel> pixels = new ArrayList<>();

        int width = resizedImage.getWidth();
        int height = resizedImage.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = resizedImage.getRGB(x, y);

                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                int[] pixelValues = {red, green, blue};
                pixels.add(new Pixel(pixelValues));
            }
        }

        return pixels;
    }


    private List<Pixel> kMeansClustering(List<Pixel> pixels, int numClusters) {
        // Create a k-means clusterer with k clusters
        KMeansPlusPlusClusterer<Pixel> clusterer = new KMeansPlusPlusClusterer<>(numClusters);
        // Cluster the pixels using the k-means algorithm
        List<CentroidCluster<Pixel>> clusters = clusterer.cluster(pixels);

        // Create a list to store the clustered pixels
        List<Pixel> clusteredPixels = new ArrayList<>();

        // Iterate through each cluster
        for (CentroidCluster<Pixel> cluster : clusters) {
            // Get the centroid values of the cluster
            double[] centroidValues = cluster.getCenter().getPoint();
            int[] centroidIntValues = new int[centroidValues.length];

            // Convert the centroid values to integers
            for (int i = 0; i < centroidValues.length; i++) {
                centroidIntValues[i] = (int) centroidValues[i];
            }
            // Create a new Pixel object with the centroid value
            Pixel centroid = new Pixel(centroidIntValues);

            // Add the centroid to the list of clustered pixels
            clusteredPixels.add(centroid);
        }
        // Return the list of clustered pixels
        return clusteredPixels;
    }


}
