package com.anji.sel.light;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

@Slf4j
public class LightHouse {

  public static void analyse(String url, String channel) throws Exception {

    ProcessBuilder processBuilder =
        new ProcessBuilder("zsh", "-c", "./run_lighthouse.sh" + " " + url + " " + channel);
    processBuilder.redirectErrorStream(true);
    processBuilder.environment().put("PATH", getPath());
    Process process = processBuilder.start();
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(process.getInputStream()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        log.info(line);
      }
    }
    process.waitFor();
    String report = new String(Files.readAllBytes(Paths.get("target/lighthouse-report.json")));
    JSONObject jsonReport = new JSONObject(report);
    double performanceScore =
        jsonReport.getJSONObject("categories").getJSONObject("performance").getDouble("score");
    log.info("Performance Score: " + performanceScore);
  }

  private static String getPath() {
    String homeDirectory = System.getProperty("user.home");
    String nodePath = homeDirectory + "/.nvm/versions/node/v21.5.0/bin";
    String currentPath = System.getenv("PATH");
    return nodePath + ":" + currentPath;
  }
}
