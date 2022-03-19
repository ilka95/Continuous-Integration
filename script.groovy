
pipeline {
  agent any
  stages {
    stage('Run Docker') {
      steps {
        sh(script:'''
          whoami
          echo '123' > passwor.secret
          sudo -S docker run --name mynginx -p 9889:80 -d nginx < passwor.secret;
        ''')
      }
    }
  }
}
