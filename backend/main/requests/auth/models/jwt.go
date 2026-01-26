package models

import (
	"github.com/golang-jwt/jwt/v5"
)

type Claims struct {
	UserId string `json:"userId"`
	jwt.RegisteredClaims
}
