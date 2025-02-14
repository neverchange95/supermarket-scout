import os
from dotenv import load_dotenv

load_dotenv()

class Config:
    @staticmethod
    def get_database_url(fallback=False):
        """
        Liefert die DATABASE_URL.
        Gibt die Fallback-URL zurück, wenn `fallback=True` ist.
        """
        if fallback:
            return "sqlite:///fallback.db"
        return os.getenv(
            "DATABASE_URL",
            "postgresql+psycopg2://admin:secret@database:5432/cnd-project-db"
        )


    @staticmethod
    def get_db_config():
        """
        Liefert die Datenbankkonfiguration als Dictionary, falls einzelne Parameter benötigt werden.
        """
        return {
            "host": os.getenv("DB_HOST", "database"),
            "database": os.getenv("DB_DATABASE", "cnd-project-db"),
            "user": os.getenv("DB_USER", "admin"),
            "password": os.getenv("DB_PASSWORD", "secret"),
            "port": int(os.getenv("DB_PORT", 5432))
        }
