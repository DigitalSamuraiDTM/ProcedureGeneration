package hello

import (
	"net/http"

	"github.com/go-chi/chi/v5"
)

func RegisterHelloRequest(router *chi.Mux) {
	router.Get("/", func(w http.ResponseWriter, r *http.Request) {
		w.Write([]byte("Hello world!"))
	})
}
