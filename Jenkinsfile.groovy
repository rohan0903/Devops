node {
stage('checkout')
git credentialsId: '8520f0a3-f733-41f6-993b-c3bf196cd7ba'
url: 'https://github.com/rohan0903/Devops.git'
node {
   echo 'Hello World'
}
stage('build/compile')
script: 'mvn clean package'
stage('archive the artifacts')
archiveArtifacts 'target/*.jar'
stage('emailnotification')
emailext body: 'Hi Rohan', subject: 'message', to: 'mogadampally.rohan@capgemini.com'
}
