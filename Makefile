# Makefile hecho por que si, ya que -.-

# Config

PROJECT_NAME := lpii
COMPOSE := docker compose
DEV_ENV_FILE := ./etc/secrets/.env.dev
PROD_ENV_FILE := ./etc/secrets/.env.prod


.PHONY: help dev prod down clean logs ps

help:
	@echo "Available targets:"
	@echo "  make dev   - Build & run development environment"
	@echo "  make prod  - Build & run production environment"
	@echo "  make down  - Stop all running containers"
	@echo "  make logs  - Show docker compose logs (follow)"
	@echo "  make ps    - List running containers"
	@echo "  make clean - Remove containers, volumes, and networks"


dev:
	@echo "🚀 Starting development environment..."
	@if [ ! -f $(DEV_ENV_FILE) ]; then \
		echo "❌ Missing $(DEV_ENV_FILE)!"; \
		exit 1; \
	fi
	$(COMPOSE) --env-file $(DEV_ENV_FILE) up --build

prod:
	@echo "🚀 Starting production environment..."
	@if [ ! -f $(PROD_ENV_FILE) ]; then \
		echo "$(RED)❌ Missing $(PROD_ENV_FILE)!"; \
		exit 1; \
	fi
	$(COMPOSE) --env-file $(PROD_ENV_FILE) up --build -d
	@echo "✅ Production environment is up!"

down:
	@echo "🧹 Stopping all containers..."
	$(COMPOSE) down

clean:
	@echo "🔥 Removing containers, images, volumes and networks..."
	$(COMPOSE) down -v --rmi all --remove-orphans

logs:
	$(COMPOSE) logs -f

ps:
	$(COMPOSE) ps
