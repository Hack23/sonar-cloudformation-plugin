# sonar-cloudformation-plugin
Cloudformation template rules (cfn-nag (https://github.com/stelligent/cfn_nag)

[![license](https://img.shields.io/github/license/Hack23/sonar-cloudformation-plugin.svg)](https://github.com/Hack23/sonar-cloudformation-plugin/raw/master/LICENSE.txt)
[![Maven Central](https://img.shields.io/maven-central/v/com.hack23.sonar/sonar-cloudformation-plugin.svg)](http://mvnrepository.com/artifact/com.hack23.sonar/sonar-cloudformation-plugin)
[![Jenkins](https://img.shields.io/jenkins/s/https/www.hack23.com/jenkins/job/Hack23/job/sonar-cloudformation-plugin/job/master.svg)](https://www.hack23.com/jenkins/job/Hack23/job/sonar-cloudformation-plugin/job/master/)
[![Jenkins tests](https://img.shields.io/jenkins/t/https/www.hack23.com/jenkins/job/Hack23/job/sonar-cloudformation-plugin/job/master.svg)](https://www.hack23.com/jenkins/job/Hack23/job/sonar-cloudformation-plugin/job/master/lastCompletedBuild/testReport/)
[![SonarQube Coverage](https://www.hack23.com/sonar/api/badges/measure?key=com.hack23.sonar%3Asonar-cloudformation-plugin&metric=coverage)](https://www.hack23.com/sonar/component_measures/domain/Coverage?id=com.hack23.sonar%3Asonar-cloudformation-plugin)
[![Lines of Code](https://www.hack23.com/sonar/api/project_badges/measure?project=com.hack23.sonar%3Asonar-cloudformation-plugin&metric=ncloc)](https://www.hack23.com/sonar/dashboard?id=com.hack23.sonar%3Asonar-cloudformation-plugin)
[![SonarQube Tech Debt](https://www.hack23.com/sonar/api/badges/measure?key=com.hack23.sonar%3Asonar-cloudformation-plugin&metric=sqale_debt_ratio)](https://www.hack23.com/sonar/component_measures?id=com.hack23.sonar%3Asonar-cloudformation-plugin)
[![Quality Gate](https://www.hack23.com/sonar/api/project_badges/measure?project=com.hack23.sonar%3Asonar-cloudformation-plugin&metric=alert_status)](https://www.hack23.com/sonar/dashboard?id=com.hack23.sonar%3Asonar-cloudformation-plugin)
[![Black Duck Security Risk](https://copilot.blackducksoftware.com/github/repos/Hack23/sonar-cloudformation-plugin/branches/master/badge-risk.svg)](https://copilot.blackducksoftware.com/github/repos/Hack23/sonar-cloudformation-plugin/branches/master)
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2FHack23%2Fsonar-cloudformation-plugin.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2FHack23%2Fsonar-cloudformation-plugin?ref=badge_shield)

# Runtime

[![JDK-8](https://img.shields.io/badge/jdk-8-green.svg)]
[![JDK-11 or higher](https://img.shields.io/badge/jdk-11-green.svg)]
[![JDK-12](https://img.shields.io/badge/jdk-12-orange.svg)]
[![JDK-13](https://img.shields.io/badge/jdk-13-orange.svg)]


# Demo links

Demo Sonarqube quality profile : https://www.hack23.com/sonar/profiles/show?language=cfn&name=Cloudformation+Rules

Demo Sonarqube quality rules : https://www.hack23.com/sonar/coding_rules?languages=cfn

Demo Sonarqube quality issues : https://www.hack23.com/sonar/project/issues?id=com.hack23.cia%3Acia-all&languages=cfn&resolutions=WONTFIX


# Support

Sonarqube 7.7+ and currently only supports cfn_nag reports (https://github.com/stelligent/cfn_nag)

# Howto

Prepare cfn_nag reports running (cfn_nag --output-format=json src/main/config/template.yml > target/template.yml.nag)
and set the property sonar.cfn.nag.reportFile=target/template.yml.nag

Or scan directories using cfn_nag_scan running (cfn_nag_scan  --input-path src/main/config/ -o json -> target/cfn-nag-scan.nagscan) and set the property sonar.cfn.nag.reportFile=target/cfn-nag-scan.nagscan 

# Properties supported 

sonar.cfn.nag.reportFiles=target/template.yml.nag,target/cfn-nag-scan.nagscan 

One or multiple .nag or .nagscan files, note for .nag files the filename should be template filename appended with .nag and for nag_scan any filename with .nagscan suffix.



# Group the cfn-nag rules to CWE

CWE-311 - Missing Encryption of Sensitive Data

CWE-732 - Incorrect Permission Assignment for Critical Resource

CWE-257 - Storing Passwords in a Recoverable Format

CWE-778 - Insufficient Logging

CWE-272 - Least Privilege Violation

CWE-286 - Incorrect User Management

No CWE associated

# Roadmap

Support more cloudformation checkers and add more rules for cfn_nag.  

## License
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2FHack23%2Fsonar-cloudformation-plugin.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2FHack23%2Fsonar-cloudformation-plugin?ref=badge_large)