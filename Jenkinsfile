pipeline {
   agent any

   tools {
        maven 'Maven'
        jdk 'Java11'
    }

   parameters {
       booleanParam(name: "RELEASE",
               description: "Build a release from current commit.",
               defaultValue: false)
   }

   stages {

	   stage('Build') {
	      steps {
	         sh "mvn clean install site"
	         sh "rm -rf target/site/jacoco-aggregate"
	         sh "rm -rf target/site/jacoco-ut"
	         sh "rm -rf target/site/jacoco-it"
	      }
	        post {
                always {
                    junit 'target/surefire-reports/*.xml'

                    jacoco(
				      execPattern: 'target/jacoco.exec',
				      classPattern: 'target/classes',
				      sourcePattern: 'src/main/java',
				      exclusionPattern: 'src/test*'
				   )

                }
            }
	   }

	   stage('Test Project') {
	      steps {
	      	 dir('test-project-with-reports') {
     		   sh "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar -Dsonar.host.url=http://192.168.1.15:9000/sonar/"
             }
	      }
	   }

	   stage('Vulnerability Check') {
	   	  tools {
    	    jdk 'Java8'
	    	}

	      steps {
	         sh "mvn org.owasp:dependency-check-maven:check -Dformat=ALL -DsuppressionFile=src/config/suppressions.xml  -DfailOnError=false"
	      }
	   }


	   stage('Dependency Update Check') {
	      steps {
	         sh "mvn org.codehaus.mojo:versions-maven-plugin:2.8.1:dependency-updates-report -DdependencyUpdatesReportFormats=html,xml"
	      }
	   }


	   stage('Verify Sonarqube Quality Gate') {
	      steps {
	         sh "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar -Dsonar.host.url=http://192.168.1.15:9000/sonar/ -Dsonar.cfn.nag.reportFiles=src/main/resources/cfn-nag-scan.nagscan -Dsonar.cfn.checkov.reportFiles=src/test/resources/checkov/cia-dist-cloudformation.checkov-report -Dsonar.sources=. -Dsonar.dependencyCheck.xmlReportPath=target/dependency-check-report.xml -Dsonar.dependencyCheck.htmlReportPath=target/dependency-check-report.html"
	      }
	   }

	   stage('Release') {
	   	 environment {
           MAVEN_OPTS = '-server -Xmx6048m -Xms6048m -Duser.timezone=CET --illegal-access=warn --add-exports java.base/sun.nio.ch=ALL-UNNAMED --add-exports java.base/jdk.internal.ref=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED --add-opens java.base/java.text=ALL-UNNAMED --add-opens java.desktop/java.awt.font=ALL-UNNAMED'
         }

            when {
                expression { params.RELEASE }
            }
            steps {
                sh "git reset --hard origin/master"
                sh "git checkout -f master"
                sh "git reset --hard origin/master"
                sh "mvn -B clean"
                sh "mvn -B release:prepare"
                sh "mvn -B release:perform -Dgoals=deploy -Darguments='-Dgoals=deploy'"
                }
       }

	   stage('Results') {
	      steps {
	          archive 'target/*.jar'
	      }
	   }
   }

   post {
        always {
            cleanWs()
        }
    }
}