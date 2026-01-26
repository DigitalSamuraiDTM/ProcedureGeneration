package auth

import (
	"backend/main/services"
	"encoding/json"
	"net/http"

	"github.com/go-chi/chi/v5"
)

type authRequestDto struct {
	Login    string `json:"login"`
	Password string `json:"password"`
}

type authResponseDto struct {
	Token string `json:"token"`
}

func RegisterAuthRequest(router *chi.Mux, auth *services.ServiceAuth) {
	router.Post("/auth", func(w http.ResponseWriter, r *http.Request) {
		defer r.Body.Close()
		var authRequest authRequestDto
		if err := json.NewDecoder(r.Body).Decode(&authRequest); err != nil {
			w.WriteHeader(http.StatusBadRequest)
			return
		}
		token, err := auth.Login(authRequest.Login, authRequest.Password)
		if err != nil {
			w.WriteHeader(http.StatusInternalServerError)
			return
		}
		w.Header().Set("Content-Type", "application/json")
		w.WriteHeader(http.StatusOK)
		if err := json.NewEncoder(w).Encode(authResponseDto{
			Token: token,
		}); err != nil {
			http.Error(w, "Fail encode response", http.StatusInternalServerError)
		}

	})
}
