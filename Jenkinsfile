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
                echo 'Kodlar build ediliyor (Testler atlanarak)...'
                // Windows için 'bat' komutu kullanılır
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('3. Unit Tests') {
            steps {
                echo 'Birim Testleri çalıştırılıyor...'
                bat 'mvn test -Dtest=*ServiceTest'
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
                bat 'mvn test -Dtest=*IntegrationTest'
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
                // Önce varsa eski container'ı durdur ve sil (Hata vermemesi için || exit 0)
                bat 'docker rm -f staj-app-container || exit 0'

                // Yeni Docker imajını oluştur
                bat 'docker build -t ogrenci-staj-app .'

                // Yeni container'ı başlat
                bat 'docker run -d -p 8081:8080 --name staj-app-container ogrenci-staj-app'

                echo 'Uygulamanın ayağa kalkması için 20 saniye bekleniyor...'
                // Windows'ta bekleme komutu 'timeout'
                bat 'timeout /t 20'
            }
        }

        stage('6. Selenium UI Tests') {
            parallel {
                stage('UI: Login Scenarios') {
                    steps {
                        echo 'Giriş Senaryoları Test Ediliyor...'
                        bat 'mvn test -Dtest=SeleniumUITest#testSuccessfulLogin,SeleniumUITest#testFailedLogin,SeleniumUITest#testEmptyLogin'
                    }
                }
                stage('UI: Navigation Scenarios') {
                    steps {
                        echo 'Navigasyon Senaryoları Test Ediliyor...'
                        bat 'mvn test -Dtest=SeleniumUITest#testPageTitle,SeleniumUITest#testDashboardVisibility,SeleniumUITest#testMenuNavigation,SeleniumUITest#testLogout'
                    }
                }
                stage('UI: Content Scenarios') {
                    steps {
                        echo 'İçerik Senaryoları Test Ediliyor...'
                        bat 'mvn test -Dtest=SeleniumUITest#testFooterText,SeleniumUITest#testIlanListesi,SeleniumUITest#testIlanEkleButonu'
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
            echo 'Pipeline tamamlandı. Temizlik yapılıyor...'
            // Çalışma bittikten sonra container'ı her zaman kaldır
            bat 'docker rm -f staj-app-container || exit 0'
        }
    }
}
