node {
stage('checkout')
git credentialsId: '8520f0a3-f733-41f6-993b-c3bf196cd7ba',url: 'https://github.com/rohan0903/Devops.git'
stage('build/compile')
sh label: '',script: 'mvn clean compile package'
stage('archive the artifacts')
archiveArtifacts 'target/*.jar'
stage('nexus artifact uploader')
nexusartifactUploader artifacts: [[artifactId: 'myproject-0.0.1-SNAPSHOT', classifier: '', file: '/var/lib/jenkins/workspace/Spring_boot_Pipeline/target/myproject-0.0.1-SNAPSHOT.jar', type: 'jar']], credentialsId: '4e4d43ee-7c26-459f-a5c2-b36a6896266e', groupId: 'com.cg', nexusUrl: '34.245.61.112:8000', nexusVersion: 'nexus3', protocol: 'http', repository: 'Springboot', version: '1.0'
 agent any 
  environment {
  PATH = "${PATH}:${getTerraformPath()}"
}
  stages{
   stage('terraform init and apply -dev'){
     steps{
       sh  returnStatus: true, script: 'terraform workspace new dev'
       sh "terraform init"
       sh "terraform apply -var-file=dev.tfvars -auto-approve"
     }
    }
    stage('terraform init and apply -prod'){
     steps{
       sh  returnStatus: true, script: 'terraform workspace new prod'
       sh "terraform init"
       sh "terraform apply -var-file=prod.tfvars -auto-approve"
     }
    }
     stage('S3 - create bucket'){
      steps{
        script{
          createS3Bucket('javahome-tf-1234')
        }
      }
    }
  }
}

def getTerraformPath() {
  def tfHome = tool  name: 'Terraform', type: 'org.jenkinsci.plugins.terraform.TerraformInstallation'
  return tfHome
}

def createS3Bucket(bucketName){
  sh returnStatus: true, script: "aws s3 mb ${bucketName} --region=eu-west-1"
}


    
     
