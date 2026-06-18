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
        OpenCV.loadLocally();

        Mat img1 = Imgcodecs.imread("finger1.png");
        Mat img2 = Imgcodecs.imread("finger2.png");

        if (img1.empty() || img2.empty()) {
            System.out.println("Could not load one of the images.");
            return;
        }

        Mat gray1 = new Mat();
        Mat gray2 = new Mat();

        Imgproc.cvtColor(img1, gray1, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(img2, gray2, Imgproc.COLOR_BGR2GRAY);

        ORB orb = ORB.create(2000);

        MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
        MatOfKeyPoint keypoints2 = new MatOfKeyPoint();

        Mat descriptors1 = new Mat();
        Mat descriptors2 = new Mat();

        orb.detectAndCompute(gray1, new Mat(), keypoints1, descriptors1);
        orb.detectAndCompute(gray2, new Mat(), keypoints2, descriptors2);

        if (descriptors1.empty() || descriptors2.empty()) {
            System.out.println("No descriptors found.");
            return;
        }

        BFMatcher matcher = BFMatcher.create(Core.NORM_HAMMING, true);
        MatOfDMatch matches = new MatOfDMatch();

        matcher.match(descriptors1, descriptors2, matches);

        List<DMatch> matchList = matches.toList();

        matchList.sort(Comparator.comparingDouble(m -> m.distance));

        List<DMatch> goodMatches = new ArrayList<>();

        for (DMatch match : matchList) {
            if (match.distance < 60) {
                goodMatches.add(match);
            }
        }

        double matchQuality = matchList.isEmpty()
        ? 0
        : (goodMatches.size() * 100.0) / matchList.size();

        System.out.println("Total matches: " + matchList.size());
        System.out.println("Good matches: " + goodMatches.size());
        System.out.printf("Match quality: %.2f%%%n", matchQuality);

            if (matchQuality >= 80) {
                System.out.println("MATCH");
            } else if (matchQuality >= 60) {
                System.out.println("POSSIBLE MATCH");
            } else {
                System.out.println("NO MATCH");
            }
                }
}