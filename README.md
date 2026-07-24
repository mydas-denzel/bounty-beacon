# BountyBeacon

BountyBeacon is a bug bounty program aggregator that polls HackerOne, Bugcrowd, and Intigriti for new and updated programs and sends notifications via Discord webhooks.

## Features

- **Multi-Provider Support**: Polls HackerOne, Bugcrowd, and Intigriti.
- **Discord Notifications**: Real-time notifications for new and updated programs.
- **Persistence**: Stores program data in PostgreSQL.
- **Polling Scheduler**: Configurable polling intervals.
- **REST API**: Endpoint to retrieve discovered programs.

## Getting Started

### Prerequisites

- Docker and Docker Compose
- API Keys for providers (optional, but recommended for higher rate limits)
- Discord Webhook URL

### Running with Docker

1. Create a `.env` file with your credentials:
   ```env
   DISCORD_WEBHOOK_URL=your_webhook_url
   H1_USERNAME=your_h1_username
   H1_API_KEY=your_h1_api_key
   BC_API_KEY=your_bc_api_key
   INT_API_KEY=your_int_api_key
   ```
2. Start the application:
   ```bash
   docker-compose up -d
   ```

### API Endpoints

- `GET /api/v1/programs`: List all programs (paginated)
- `GET /api/v1/programs/{id}`: Get program details
