package main

import (
	"backend/environment"
	"backend/main/requests/auth"
	"backend/main/requests/check"
	"backend/main/requests/hello"
	"backend/main/services"
	"context"
	"fmt"
	"log"
	"net/http"
	"os"
	"os/signal"
	"time"

	"github.com/go-chi/chi/v5"
	"github.com/go-chi/chi/v5/middleware"
)

func main() {

	envData := environment.LoadEnvironmentData()
	authService := services.InitAuthService(envData.Services.Auth)
	otelService, err := services.InitOtelService(context.Background(), envData.Services.Otel)
	if err != nil {
		log.Fatal(err)
	}
	defer func() {
		if err := otelService.Tracer.Shutdown(context.Background()); err != nil {
			log.Fatal(err)
		}
	}()

	router := chi.NewRouter()
	otelService.SetupMiddleware(router)

	// Middleware
	router.Use(middleware.RequestID)
	router.Use(middleware.RealIP)
	router.Use(middleware.Logger)
	router.Use(middleware.Recoverer)

	// Routes
	auth.RegisterAuthRequest(router, authService)
	check.RegisterCheckRequest(router, authService)
	hello.RegisterHelloRequest(router)

	server := &http.Server{
		Addr:    fmt.Sprintf("%s:%d", envData.Host, envData.Port),
		Handler: router,
	}
	go func() {
		log.Println(fmt.Sprintf("Server started on %s:%d", envData.Host, envData.Port))
		if err := server.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Fatal(err)
		}
	}()

	stop := make(chan os.Signal, 1)
	signal.Notify(stop, os.Interrupt)
	<-stop

	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	server.Shutdown(ctx)

}
