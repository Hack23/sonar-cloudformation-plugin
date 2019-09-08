# sonar-cloudformation-plugin
Cloudformation template rules (cfn-nag (https://github.com/stelligent/cfn_nag)

[![license](https://img.shields.io/github/license/Hack23/sonar-cloudformation-plugin.svg)](https://github.com/Hack23/sonar-cloudformation-plugin/raw/master/LICENSE.txt)
[![Maven Central](https://img.shields.io/maven-central/v/com.hack23.sonar/sonar-cloudformation-plugin.svg)](http://mvnrepository.com/artifact/com.hack23.sonar/sonar-cloudformation-plugin)
[![Jenkins](https://img.shields.io/jenkins/s/https/www.hack23.com/jenkins/view/Tools/job/sonar-cloudformation-plugin.svg)](https://www.hack23.com/jenkins/view/Tools/job/sonar-cloudformation-plugin/)
[![Jenkins tests](https://img.shields.io/jenkins/t/https/www.hack23.com/jenkins/view/Tools/job/sonar-cloudformation-plugin.svg)](https://www.hack23.com/jenkins/view/Tools/job/sonar-cloudformation-plugin/lastCompletedBuild/testReport/)
[![SonarQube Coverage](https://www.hack23.com/sonar/api/badges/measure?key=com.hack23.sonar%3Asonar-cloudformation-plugin&metric=coverage)](https://www.hack23.com/sonar/component_measures/domain/Coverage?id=com.hack23.sonar%3Asonar-cloudformation-plugin)
[![Lines of Code](https://www.hack23.com/sonar/api/project_badges/measure?project=com.hack23.sonar%3Asonar-cloudformation-plugin&metric=ncloc)](https://www.hack23.com/sonar/dashboard?id=com.hack23.sonar%3Asonar-cloudformation-plugin)
[![SonarQube Tech Debt](https://www.hack23.com/sonar/api/badges/measure?key=com.hack23.sonar%3Asonar-cloudformation-plugin&metric=sqale_debt_ratio)](https://www.hack23.com/sonar/component_measures?id=com.hack23.sonar%3Asonar-cloudformation-plugin)
[![Quality Gate](https://www.hack23.com/sonar/api/project_badges/measure?project=com.hack23.sonar%3Asonar-cloudformation-plugin&metric=alert_status)](https://www.hack23.com/sonar/dashboard?id=com.hack23.sonar%3Asonar-cloudformation-plugin)
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2FHack23%2Fsonar-cloudformation-plugin.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2FHack23%2Fsonar-cloudformation-plugin?ref=badge_large)

# Runtime

[![JDK-8](https://img.shields.io/badge/jdk-8-green.svg)]
[![JDK-11 or higher](https://img.shields.io/badge/jdk-11-greem.svg)]
[![JDK-12](https://img.shields.io/badge/jdk-12-orange.svg)]
[![JDK-13](https://img.shields.io/badge/jdk-13-orange.svg)]


# Support

Sonarqube 7.7+ and currently only cfn_nag reports (https://github.com/stelligent/cfn_nag)

# Howto

Prepare cfn_nag reports running (cfn_nag src/main/config/template.yml > target/template.yml.nag)
and set the property sonar.cfn.nag.reportFile=target/template.yml.nag

Or scan directories using cfn_nag_scan running (cfn_nag_scan  --input-path src/main/config/ -o json -> target/cfn-nag-scan.nagscan) and set the property sonar.cfn.nag.reportFile=target/cfn-nag-scan.nagscan 

# Properties supported 

sonar.cfn.nag.reportFiles=target/template.yml.nag,target/cfn-nag-scan.nagscan 

One or multiple .nag or .nagscan files, note for .nag files the filename should be template filename appended with .nag and for nag_scan any filename with .nagscan suffix.

# Roadmap

Support more cloudformation checkers and add more rules for cfn_nag.  