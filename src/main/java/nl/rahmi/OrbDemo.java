package nl.rahmi;

import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.features2d.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrbDemo {
    public static void main(String[] args) {
        // Load OpenCV native libraries before using any OpenCV classes.
        OpenCV.loadLocally();

        // Read the two fingerprint images from disk.
        Mat img1 = Imgcodecs.imread("finger1.png");
        Mat img2 = Imgcodecs.imread("finger2.png");

        // Stop early if either image could not be loaded.
        if (img1.empty() || img2.empty()) {
            System.out.println("Could not load one of the images.");
            return;
        }

        // Convert both images to grayscale for feature detection.
        Mat gray1 = new Mat();
        Mat gray2 = new Mat();

        Imgproc.cvtColor(img1, gray1, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(img2, gray2, Imgproc.COLOR_BGR2GRAY);

        // Create the ORB detector and configure it to find a large number of keypoints.
        ORB orb = ORB.create(2000);

        // Store detected keypoints and descriptors for each image.
        MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
        MatOfKeyPoint keypoints2 = new MatOfKeyPoint();

        Mat descriptors1 = new Mat();
        Mat descriptors2 = new Mat();

        // Detect features and compute binary descriptors for both images.
        orb.detectAndCompute(gray1, new Mat(), keypoints1, descriptors1);
        orb.detectAndCompute(gray2, new Mat(), keypoints2, descriptors2);

        // If either image produced no descriptors, there is nothing to compare.
        if (descriptors1.empty() || descriptors2.empty()) {
            System.out.println("No descriptors found.");
            return;
        }

        // Match descriptors between the two images using Hamming distance.
        BFMatcher matcher = BFMatcher.create(Core.NORM_HAMMING, true);
        MatOfDMatch matches = new MatOfDMatch();

        matcher.match(descriptors1, descriptors2, matches);

        // Convert matches to a list so they can be sorted and filtered.
        List<DMatch> matchList = matches.toList();

        // Sort by distance, where smaller values usually mean stronger matches.
        matchList.sort(Comparator.comparingDouble(m -> m.distance));

        // Keep only matches that are considered good enough.
        List<DMatch> goodMatches = new ArrayList<>();

        for (DMatch match : matchList) {
            if (match.distance < 60) {
                goodMatches.add(match);
            }
        }

        // Calculate how many of the total matches are considered good.
        double matchQuality = matchList.isEmpty()
                ? 0
                : (goodMatches.size() * 100.0) / matchList.size();

        // Print summary information about the match quality.
        System.out.println("Total matches: " + matchList.size());
        System.out.println("Good matches: " + goodMatches.size());
        System.out.printf("Match quality: %.2f%%%n", matchQuality);

        // Decide whether the two fingerprints are likely a match.
        if (matchQuality >= 80) {
            System.out.println("MATCH");
        } else if (matchQuality >= 60) {
            System.out.println("POSSIBLE MATCH");
        } else {
            System.out.println("NO MATCH");
        }
    }
}