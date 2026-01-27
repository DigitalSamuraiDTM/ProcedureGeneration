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
	Otel OtelService
}

type AuthService struct {
	Key    string
	Issuer string
}

type OtelService struct {
	Host string
	Port int
}

func LoadEnvironmentData() *Data {
	serverPort, err := strconv.Atoi(os.Getenv("SERVER_PORT"))
	if err != nil {
		// TODO локально юзаются аргументы, поэтому нельзя кидать фатал
	}
	otelPort, err := strconv.Atoi(os.Getenv("OTEL_PORT"))
	if err != nil {
		// TODO локально юзаются аргументы, поэтому нельзя кидать фатал
	}
	data := &Data{
		Host: os.Getenv("SERVER_HOST"),
		Port: serverPort,
		Services: Services{
			Auth: AuthService{
				Key:    os.Getenv("AUTH_KEY"),
				Issuer: os.Getenv("AUTH_ISSUER"),
			},
			Otel: OtelService{
				Host: os.Getenv("OTEL_HOST"),
				Port: otelPort,
			},
		},
	}
	flag.StringVar(&data.Host, "server_host", data.Host, "Server host address")
	flag.IntVar(&data.Port, "server_port", data.Port, "Server serverPort address")

	flag.StringVar(&data.Services.Auth.Key, "auth_key", data.Services.Auth.Key, "Special secret key for auth")
	flag.StringVar(&data.Services.Auth.Issuer, "auth_issuer", data.Services.Auth.Issuer, "service")

	flag.StringVar(&data.Services.Otel.Host, "otel_host", data.Services.Otel.Host, "Otel host address")
	flag.IntVar(&data.Services.Otel.Port, "otel_port", data.Services.Otel.Port, "Otel grpc port")

	flag.Parse()
	if data.Host == "" || data.Port == 0 {
		log.Fatal("Environment data not parsed")
	}
	return data
}
