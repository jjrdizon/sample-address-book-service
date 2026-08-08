COMPOSE_FILE := tools/local-docker-compose.yaml

build:
	./mvnw package -DskipTests

up: build
	docker compose -f $(COMPOSE_FILE) up --build

down:
	docker compose -f $(COMPOSE_FILE) down