# Security Notes

MedSphere is an academic Semester 3 MCA mini project intended for local development and demonstration.

## Development Credentials

The repository contains development/demo seed accounts for academic testing. These credentials are not production credentials and must not be reused in a real hospital or production environment.

## Local Secrets

Do not commit:

- `.env` files
- real database passwords
- API keys or access tokens
- production credentials
- private certificates or keys

Use environment variables for local database credentials. See `.env.example` for the supported database environment variable names.

## Production Use

This project is not designed or security-reviewed for production healthcare use. A production deployment would require stronger secret management, HTTPS, audit logging, privacy controls, access reviews, backups, monitoring, and appropriate healthcare/data-protection compliance.
