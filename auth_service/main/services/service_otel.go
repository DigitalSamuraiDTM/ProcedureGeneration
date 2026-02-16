package services

import (
	"backend/environment"
	"context"
	"errors"
	"log"
	"strconv"

	"github.com/go-chi/chi/v5"
	"github.com/riandyrn/otelchi"
	"go.opentelemetry.io/otel"
	otelRpcTracer "go.opentelemetry.io/otel/exporters/otlp/otlptrace/otlptracegrpc"
	"go.opentelemetry.io/otel/propagation"
	otelResource "go.opentelemetry.io/otel/sdk/resource"
	otelTrace "go.opentelemetry.io/otel/sdk/trace"
	semconv "go.opentelemetry.io/otel/semconv/v1.4.0"
)

type ServiceOtel struct {
	Tracer *otelTrace.TracerProvider
}

func (service *ServiceOtel) SetupMiddleware(router chi.Router) {
	router.Use(otelchi.Middleware("AuthorizationService", otelchi.WithChiRoutes(router)))
}

func InitOtelService(ctx context.Context, data environment.OtelService) (*ServiceOtel, error) {
	tracer := initTracer(ctx, data)
	if tracer == nil {
		return nil, errors.New("otel tracer initialization failed")
	}

	service := ServiceOtel{
		Tracer: tracer,
	}

	otel.SetTracerProvider(tracer)
	otel.SetTextMapPropagator(propagation.NewCompositeTextMapPropagator(propagation.TraceContext{}, propagation.Baggage{}))

	return &service, nil
}

func initTracer(ctx context.Context, data environment.OtelService) *otelTrace.TracerProvider {
	exporter, err := otelRpcTracer.New(ctx, otelRpcTracer.WithEndpoint(data.Host+":"+strconv.Itoa(data.Port)), otelRpcTracer.WithInsecure())
	if err != nil {
		log.Fatal(err)
	}

	res, err := otelResource.New(
		context.Background(),
		otelResource.WithAttributes(
			// the service name used to display traces in backends
			semconv.ServiceNameKey.String("AuthorizationService"),
		),
	)
	if err != nil {
		log.Fatalf("unable to initialize resource due: %v", err)
	}

	return otelTrace.NewTracerProvider(
		otelTrace.WithSampler(otelTrace.AlwaysSample()),
		otelTrace.WithBatcher(exporter),
		otelTrace.WithResource(res),
	)
}
