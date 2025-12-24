pipeline {
    agent any

    tools {
        maven 'Maven 3.9.6'
        jdk 'JDK 21'
    }

    stages {
        stage('1. Checkout') {
            steps {
                echo 'Github\'dan kodlar çekiliyor...'
                checkout scm
            }
        }

        stage('2. Build') {
            steps {
                echo 'Kodlar build ediliyor...'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('3. Unit Tests') {
            steps {
                echo 'Birim Testleri çalıştırılıyor...'
                sh 'mvn test -Dtest=*ServiceTest'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('4. Integration Tests') {
            steps {
                echo 'Entegrasyon Testleri çalıştırılıyor...'
                sh 'mvn test -Dtest=*IntegrationTest'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('5. Dockerize') {
            steps {
                echo 'Sistem docker container üzerinde çalıştırılıyor...'
                sh 'docker build -t ogrenci-staj-app .'
                sh 'docker rm -f staj-app-container || true'
                sh 'docker run -d -p 8081:8080 --name staj-app-container ogrenci-staj-app'
                echo 'Uygulamanın ayağa kalkması bekleniyor...'
                sleep 20
            }
        }

        stage('6. Selenium UI Tests') {
            // Selenium testlerini alt stage'lere bölüyoruz
            parallel {
                stage('UI: Login Scenarios') {
                    steps {
                        echo 'Giriş Senaryoları Test Ediliyor...'
                        // Başarılı giriş, Hatalı giriş, Boş giriş
                        sh 'mvn test -Dtest=SeleniumUITest#testSuccessfulLogin,SeleniumUITest#testFailedLogin,SeleniumUITest#testEmptyLogin'
                    }
                }
                stage('UI: Navigation Scenarios') {
                    steps {
                        echo 'Navigasyon Senaryoları Test Ediliyor...'
                        // Sayfa başlığı, Dashboard görünürlüğü, Menü navigasyonu, Çıkış yapma
                        sh 'mvn test -Dtest=SeleniumUITest#testPageTitle,SeleniumUITest#testDashboardVisibility,SeleniumUITest#testMenuNavigation,SeleniumUITest#testLogout'
                    }
                }
                stage('UI: Content Scenarios') {
                    steps {
                        echo 'İçerik Senaryoları Test Ediliyor...'
                        // Footer metni, İlan listesi, İlan ekleme butonu
                        sh 'mvn test -Dtest=SeleniumUITest#testFooterText,SeleniumUITest#testIlanListesi,SeleniumUITest#testIlanEkleButonu'
                    }
                }
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline tamamlandı.'
            sh 'docker rm -f staj-app-container || true'
        }
    }
}
