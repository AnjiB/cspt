# Capture and Analyse Web UI performance metrics through Selenium Functional tests


This project aims to conduct comprehensive client-side performance testing using Google Lighthouse and Selenium 4 Chrome DevTools Protocol (CDP). It captures important UI performance metrics through functional tests, stores them in a MySQL database, and visualizes trends in Grafana. This setup helps detect UI responsiveness issues early and provides actionable insights for improvement, ultimately ensuring a smooth user experience.

## Key Features

1. **Comprehensive UI Performance Monitoring**: Measures performance metrics on the frontend, capturing important data directly from the browser.
2. **Metrics Storage and Visualization**: Stores metrics in a MySQL database and visualizes performance trends using Grafana.
3. **Automated Alerts**: Configurable alerts notify the development team when metrics fall below pre-set thresholds, ensuring prompt action on UI performance degradation.

## Project Components

- **Spring Boot Service**: Exposes a POST API to accept and store performance metrics.
- **MySQL Database**: Persists captured metric data for trend analysis.
- **Grafana Dashboard**: Visualizes performance metrics, trends, and SLOs, enabling quick insights into UI performance.
- **Docker Compose Setup**: Orchestrates all components (API, MySQL, and Grafana) for local or cloud deployment.

## Metrics Captured

The following performance metrics are captured through Google Lighthouse and Selenium CDP:

### Google Lighthouse Metrics




| Metric                  | Description                                             |
| ----------------------- |:-------------:                                          |
| Performance Score       | Overall score based on multiple performance metrics     |
| Accessibility Score     | Measures the accessibility of the webpage               |
| Cumulative Layout Shift | Measures visual stability                               |
| First Contentful Paint  | Time taken to render the first content                  |
| Largest Contentful Paint| Time taken to render the largest content                |
| First Meaningful Paint  | Time taken for primary content to load                  |
| Speed Index             | Measures loading speed of visible content               |
| Total Blocking Time     | Time the page was blocked due to script execution       |
| Server Response Time    | Time taken by the server to respond                     |
| Total Byte Weight       | Total bytes used by page resources                      |
| Uses Long Cache TTL     | Indicates if caching policies are optimal               |



### Selenium CDP Metrics

| Metric                   | Description |
|--------------------------|-------------|
| DomContentLoaded         | Time when initial HTML document was completely loaded |
| JSHeapTotalSize          | Total size of the JavaScript heap |
| JSHeapUsedSize           | Amount of JavaScript heap in use |
| Script Duration          | Time spent executing JavaScript |

## How It Works

1. **Functional Tests**: Google Lighthouse and Selenium 4 CDP are run as part of UI functional tests to capture metrics.
2. **Data Transformation**: The metrics are processed and filtered, then sent to the Spring Boot API for storage.
3. **Trend Visualization**: Grafana pulls data from MySQL, displaying key performance metrics and trends.
4. **Alerts and SLOs**: Set up SLOs and alerts in Grafana to proactively manage performance issues.

## Setup and Installation

### Prerequisites

- Docker and Docker Compose
- Git
- Maven
- Java 17 (for running the Spring Boot service)
- Node.js (for Lighthouse)

### Running the Project
 
 **Install Lighthouse**
 
 ````
 npm install -g lighthouse
 
 ````
 
 **Clone the Repository**:

  ````
   git clone https://github.com/AnjiB/cspt.git
   cd cspt-metric-service
   docker-compose up --build
   
   ````
  
These commands starts all necessary services including the Spring Boot API, MySQL database, and Grafana.
 

Execute Lighthouse and Selenium CDP tests as part of your CI/CD pipeline to capture UI performance metrics.

## Accessing Grafana:

Grafana will be accessible at http://localhost:3000 (default login: admin/admin).

## API Endpoints
The Spring Boot API accepts POST requests to store metrics.

**POST:** /api/performance-metrics

**Body:** A JSON array of performance metrics including document, name, timestamp, and value.
 
**Sample Payload:**


````

[
  {
    "document": "https://automationteststore.com/",
    "name": "Performance Score",
    "timestamp": "2024-10-30T12:34:56Z",
    "value": 85.0
  },
  {
    "document": "https://automationteststore.com/",
    "name": "First Contentful Paint",
    "timestamp": "2024-10-30T12:34:56Z",
    "value": 2.5
  }
]

`````

## Code Overview

### Lighthouse Testing

[LightHouseMetricsTest](../src/test/java/com/anji/ui/LightHouseMetricsTest.java): A test class for running Lighthouse performance tests and sending metrics to the API.

### Selenium CDP Testing

[UIPerformanceTest](../src/test/java/com/anji/ui/UIPerformanceTest.java): A test class for capturing performance metrics via Selenium CDP.
Utilities

[TransformUtil](../src/main/java/com/anji/sel/util/TransformUtil.java): Transforms raw metrics into a standardized format.

[FilterUtil](../src/main/java/com/anji/sel/util/FilterUtil.java): Filters out irrelevant metrics before storage.

### Spring Boot API Client

[PerformanceClient](../src/main/java/com/anji/sel/PerformanceClient.java): A client class for sending metrics to the Spring Boot API.


## Future Enhancements

**Enhanced Alerts:** Integrate with alerting tools for real-time notifications on metric drops.

**Additional Metrics:** Capture further UI metrics, like image load times or CSS render times.

**AI-Powered Insights:** Implement AI-based predictive analysis on performance trends for proactive issue detection.

# Grafana Visuals

## Lighthouse - Performance & Accessbility Score

![image info](https://github.com/AnjiB/sel-four/blob/main/docs/images/lighthouse-scores.png)

## Lighthouse - Performance Metrics

![image info](https://github.com/AnjiB/sel-four/blob/main/docs/images/lighthouse-metrics.png)


## Selenium 4 CDP - Performance Metrics

![image info](https://github.com/AnjiB/sel-four/blob/main/docs/images/cdp-peformance-metrics.png)




