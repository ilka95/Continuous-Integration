
pipeline {
  agent { label 'мастер' }
  stages {
    stage('Run Docker') {
      steps {
        sh(script:'''
          docker run --name mynginx1 -p 80:9889 -d nginx;
        ''')
      }
    }
  }
}
