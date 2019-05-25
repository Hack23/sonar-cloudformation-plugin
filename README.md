# sonarqube-cloudformation-plugin
Cloudformation template rules (cgn-nag,...)

[![license](https://img.shields.io/github/license/Hack23/sonarqube-cloudformation-plugin.svg)](https://github.com/Hack23/sonarqube-cloudformation-plugin/raw/master/LICENSE.txt)
[![Jenkins](https://img.shields.io/jenkins/s/https/www.hack23.com/jenkins/view/Tools/job/sonarqube-cloudformation-plugin.svg)](https://www.hack23.com/jenkins/view/Tools/job/sonarqube-cloudformation-plugin/)
[![Jenkins tests](https://img.shields.io/jenkins/t/https/www.hack23.com/jenkins/view/Tools/job/sonarqube-cloudformation-plugin.svg)](https://www.hack23.com/jenkins/view/Tools/job/sonarqube-cloudformation-plugin/lastCompletedBuild/testReport/)
[![SonarQube Coverage](https://www.hack23.com/sonar/api/badges/measure?key=com.hack23.sonar%3Asonar-cloudformation-plugin&metric=coverage)](https://www.hack23.com/sonar/component_measures/domain/Coverage?id=com.hack23.sonar%3Asonar-cloudformation-plugin)
[![Lines of Code](https://www.hack23.com/sonar/api/project_badges/measure?project=com.hack23.sonar%3Asonar-cloudformation-plugin&metric=ncloc)](https://www.hack23.com/sonar/dashboard?id=com.hack23.sonar%3Asonar-cloudformation-plugin)
[![SonarQube Tech Debt](https://www.hack23.com/sonar/api/badges/measure?key=com.hack23.sonar%3Asonar-cloudformation-plugin&metric=sqale_debt_ratio)](https://www.hack23.com/sonar/component_measures?id=com.hack23.sonar%3Asonar-cloudformation-plugin)
[![Quality Gate](https://www.hack23.com/sonar/api/project_badges/measure?project=com.hack23.sonar%3Asonar-cloudformation-plugin&metric=alert_status)](https://www.hack23.com/sonar/dashboard?id=com.hack23.sonar%3Asonar-cloudformation-plugin)


# Runtime

[![JDK-8](https://img.shields.io/badge/jdk-8-green.svg)]
[![JDK-11 or higher](https://img.shields.io/badge/jdk-11-orange.svg)]
[![JDK-12](https://img.shields.io/badge/jdk-12-orange.svg)]
[![JDK-13](https://img.shields.io/badge/jdk-13-orange.svg)]


# Support

Sonarqube 7.7+ and currently only cfn_nag reports (https://github.com/stelligent/cfn_nag)

# Howto

Prepare cfn_nag reports running (cfn_nag src/main/config/template.yml > target/template.yml.nagreport)
and set the sonar.cfn.nag.reportFile=target/template.yml.nagreport

# Roadmap

Support cfn-lint (https://github.com/aws-cloudformation/cfn-python-lint) 