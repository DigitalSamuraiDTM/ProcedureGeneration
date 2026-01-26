package environment

import (
	"flag"
	"log"
	"os"
	"strconv"
)

type Data struct {
	Host     string
	Port     int
	Services Services
}

type Services struct {
	Auth AuthService
}

type AuthService struct {
	Key    string
	Issuer string
}

func LoadEnvironmentData() *Data {
	port, err := strconv.Atoi(os.Getenv("SERVER_PORT"))
	if err != nil {
		// TODO
	}
	data := &Data{
		Host: os.Getenv("SERVER_HOST"),
		Port: port,
		Services: Services{
			Auth: AuthService{
				Key:    os.Getenv("AUTH_KEY"),
				Issuer: os.Getenv("AUTH_ISSUER"),
			},
		},
	}
	flag.StringVar(&data.Host, "server_host", data.Host, "Server host address")
	flag.IntVar(&data.Port, "server_port", data.Port, "Server port address")
	flag.StringVar(&data.Services.Auth.Key, "auth_key", data.Services.Auth.Key, "Special secret key for auth")
	flag.StringVar(&data.Services.Auth.Issuer, "auth_issuer", data.Services.Auth.Issuer, "service")
	flag.Parse()
	if data.Host == "" || data.Port == 0 {
		log.Fatal("Environment data not parsed")
	}
	return data
}
