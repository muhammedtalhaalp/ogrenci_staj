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

                git branch: 'main', url: 'https://github.com/muhammedtalhaalp/ogrenci_staj.git'
            }
        }

        stage('2. Build') {
            steps {
                echo 'Kodlar build ediliyor (Testler atlanarak)...'
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
                bat 'docker rm -f staj-app-container || exit 0'
                bat 'docker build -t ogrenci-staj-app .'
                bat 'docker run -d -p 8081:8080 --name staj-app-container ogrenci-staj-app'

                echo 'Uygulamanın ayağa kalkması için 20 saniye bekleniyor...'
                bat 'ping -n 21 127.0.0.1 > nul'
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

            script {
                try {
                    bat 'docker rm -f staj-app-container || exit 0'
                } catch (Exception e) {
                    echo 'Temizlik sırasında hata oluştu veya container zaten yok: ' + e.getMessage()
                }
            }
        }
    }
}
