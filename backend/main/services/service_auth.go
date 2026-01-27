package services

import (
	"backend/environment"
	"backend/main/requests/auth/models"
	"time"

	"github.com/golang-jwt/jwt/v5"
)

type ServiceAuth struct {
	secretKey string // ключик для создания jwt
	issuer    string // имя пода, который выдает токены
}

// InitAuthService key - super secret key for jwt
func InitAuthService(data environment.AuthService) *ServiceAuth {
	// TODO добавить трассировки на авторизацию!

	// todo представим, что тут мы получаем всю необходимую информацию для аутентификации и авторизации
	return &ServiceAuth{
		secretKey: data.Key,
		issuer:    data.Issuer,
	}
}

func (auth *ServiceAuth) Login(login string, password string) (string, error) {
	// TODO где-то тут поход в бд с валидацией данных

	// аутентификация ок, пользователь есть в системе, создаем токен
	generatedToken, err := auth.generateToken("TODO")
	return generatedToken, err
}

func (service *ServiceAuth) generateToken(userId string) (string, error) {
	claims := models.Claims{
		UserId: userId,
		RegisteredClaims: jwt.RegisteredClaims{
			ExpiresAt: jwt.NewNumericDate(time.Now().Add(time.Hour * time.Duration(1))),
			IssuedAt:  jwt.NewNumericDate(time.Now()),
			NotBefore: jwt.NewNumericDate(time.Now()),
			Issuer:    service.issuer,
			Subject:   userId,
		},
	}
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)

	return token.SignedString([]byte(service.secretKey))
}
