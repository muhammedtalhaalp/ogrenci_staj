pipeline {
    agent any

    tools {
        // Jenkins Global Tool Configuration'da tanımlı adlar olmalı
        maven 'Maven 3.9.6'
        jdk 'JDK 21'
    }

    stages {
        stage('Checkout') {
            steps {
                // Kodun çekilmesi
                checkout scm
            }
        }

        stage('Build Maven') {
            steps {
                // Testleri şimdilik atlıyoruz (CI ortamında Selenium için headless chrome gerekir)
                // Eğer Jenkins Windows üzerinde servis olarak çalışıyorsa 'sh' yerine 'bat' kullanın
                bat 'mvn clean package -DskipTests'
            }
        }

    }
    
    post {
        success {
            echo 'Build ve Docker imajı başarıyla oluşturuldu.'
        }
        failure {
            echo 'Pipeline başarısız oldu.'
        }
    }
}