
pipeline {
  agent any
  stages {
    stage('Run Docker') {
      steps {
        sh(script:'''
          sudo docker run --name mynginx -p 80:9889 -d nginx;
        ''')
      }
    }
  }
}
