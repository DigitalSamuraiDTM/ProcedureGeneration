package check

import (
	"backend/main/services"
	"net/http"

	"github.com/go-chi/chi/v5"
)

func RegisterCheckRequest(router *chi.Mux, auth *services.ServiceAuth) {
	router.Get("/check", func(w http.ResponseWriter, r *http.Request) {

		tokenString := r.Header.Get("Authorization")
		if tokenString == "" {
			w.WriteHeader(http.StatusUnauthorized)
			return
		}
		_, err := auth.Check(tokenString)
		if err != nil {
			w.WriteHeader(http.StatusUnauthorized)
			return
		}
		w.WriteHeader(http.StatusOK)
	})
}
