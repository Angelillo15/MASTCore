package com.nookure.staff.api.jenkins;

import com.nookure.staff.api.jenkins.json.JenkinsJob;
import com.nookure.staff.api.jenkins.json.JenkinsRun;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

public class JenkinsTest {
  private final String host = System.getenv("JENKINS_HOST") != null ? System.getenv("JENKINS_HOST") : "https://ci.nookure.com";
  private final JenkinsBaseClient jenkinsBaseClient = new JenkinsBaseClient(host);

  @Test
  @EnabledIf("canRun")
  public void testGetJob() {
    final JenkinsJob jenkinsJob = jenkinsBaseClient.getJob("NookureStaff").join();
    assert jenkinsJob != null;
  }

  @Test
  @EnabledIf("canRun")
  public void testGetRun() {
    final JenkinsJob jenkinsJob = jenkinsBaseClient.getJob("NookureStaff").join();
    final JenkinsRun jenkinsRun = jenkinsBaseClient.getRun("NookureStaff", jenkinsJob.lastCompletedBuild().number()).join();

    assert jenkinsRun != null;
  }

  public static boolean canRun() {
    return System.getenv("JENKINS_HOST") != null;
  }
}
