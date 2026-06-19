# Fingerprint Auth Service

A fingerprint comparison project built in Java using OpenCV ORB (Oriented FAST and Rotated BRIEF).

This project compares two fingerprint images and determines whether they are likely to belong to the same finger by detecting and matching visual features within the fingerprint patterns.

## Features

- Compare two fingerprint images
- Detect fingerprint keypoints using ORB
- Match fingerprint features using Brute Force Matching
- Calculate:
  - Total Matches
  - Good Matches
  - Match Quality (%)
- Display match result

## How It Works

The system uses the following workflow:

1. Load two fingerprint images
2. Convert images to grayscale
3. Detect keypoints using ORB
4. Generate descriptors for each keypoint
5. Match descriptors using Hamming distance
6. Filter matches using a distance threshold
7. Calculate match quality
8. Determine whether the fingerprints match

### Key Terms

#### Keypoints

Keypoints are unique visual features found in a fingerprint image.

Examples include:

- Ridge endings
- Ridge intersections
- Sharp curves
- Distinct local patterns

#### Total Matches

The total number of descriptor pairs found between two fingerprint images.

#### Good Matches

Matches whose Hamming distance is below the selected threshold.

Current threshold:

```java
match.distance < 60
```

#### Match Quality

Percentage of good matches compared to total matches.

Formula:

```text
Match Quality = (Good Matches / Total Matches) × 100
```

Example:

```text
Total Matches: 541
Good Matches: 508

Match Quality:
(508 / 541) × 100
=
93.90%
```

## Example Output

```text
Keypoints image 1: 2000
Keypoints image 2: 2000

Total matches: 541
Good matches: 508
Match quality: 93.90%

MATCH
```

## Technologies Used

- Java 21
- Maven
- OpenCV
- ORB Feature Detection
- BFMatcher (Brute Force Matcher)

## Project Structure

```text
fingerprint-auth-service
│
├── src
│   └── main
│       └── java
│           └── nl
│               └── rahmi
│                   └── OrbDemo.java
│
├── finger1.png
├── finger2.png
├── pom.xml
└── README.md
```

## Installation

### Clone the repository

```bash
git clone https://github.com/Zasreaaz/fingerprint-auth-service.git
cd fingerprint-auth-service
```

### Build the project

```bash
mvn clean install
```

### Run the application

```bash
mvn compile exec:java "-Dexec.mainClass=nl.rahmi.OrbDemo"
```

## Notes

This project was developed as an experimental fingerprint matching solution using image feature matching.

Unlike specialized fingerprint engines such as SourceAFIS, ORB compares visual image features and can be more tolerant of differences between separate photographs of the same fingerprint.

The accuracy of the results depends heavily on:

- Image quality
- Lighting conditions
- Fingerprint visibility
- Image alignment
- Contrast

## Future Improvements

- Visualize matched keypoints
- Automatic fingerprint cropping
- Fingerprint image enhancement
- REST API integration
- Database storage for fingerprint templates

## Author

Rahmi Tas

Software Engineering Student  
Amsterdam University of Applied Sciences (HvA)
