pipeline {
   agent any
  
   tools { 
        maven 'Maven' 
        jdk 'Java8' 
    }
    
   stages {

	   stage('Build') {
	      steps {
	         sh "mvn clean install site"
	      }
	   }

	   stage('Vulnerability Check') {
	      steps {
	         sh "mvn org.owasp:dependency-check-maven:5.2.1:check -Dformat=ALL"
	      }
	   }


	   stage('Dependency Update Check') {
	      steps {
	         sh "mvn org.codehaus.mojo:versions-maven-plugin:2.7:dependency-updates-report -DdependencyUpdatesReportFormats=html,xml"
	      }
	   }
       
	   
	   stage('Publish results to Sonarqube') {
	      steps {
	         sh "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.6.0.1398:sonar -Dsonar.host.url=http://192.168.1.15:9000/sonar/ -Dsonar.sources=. -Dsonar.dependencyCheck.reportPath=target/dependency-check-report.xml -Dsonar.dependencyCheck.htmlReportPath=target/dependency-check-report.html"
	      }
	   }
	  
	   stage('Quality Gate') {
	      steps {
	         sh "mvn com.hack23.maven:sonar-quality-gates-maven-plugin:1.0.0:inspect  -Dsonar.host.url=http://192.168.1.15:9000/sonar"
	      }
	   }
	   
	   
	   stage('Results') {
	      steps {
	          archive 'target/*.jar'
	      }
	   }
   }
}