from src.adapters.scheduler.scheduler_service import SchedulerService
from src.adapters.config.config import Config
from src.adapters.db.database import init_db, engine
from src.core.use_cases.save_scraped_data_use_case import SaveScrapedDataUseCase
from src.adapters.scraping.scraping_service import ScrapingService
from src.adapters.db.product_repository_postgres import ProductRepositoryPostgres
from src.adapters.config.category_config import CategoryConfig
from src.adapters.scraping.supermarkets_scraper import SupermarketScraper

def run_service():
    db_config = Config.get_db_config()

    # Konfigurationsadapter initialisieren
    config = CategoryConfig("src/adapters/config/whitelist.json")

    # Scraper-Service initialisieren
    service = ScrapingService(category_config=config)
    results = service.scrape_all_data()

    # DB-Adapter + UseCase initialisieren
    use_fallback = str(engine.url).startswith("sqlite")
    product_repo = ProductRepositoryPostgres(db_config=db_config, use_fallback=use_fallback)
    init_db()
    use_case = SaveScrapedDataUseCase(product_repo)

    # Supermärkte scrapen und speichern
    scraper = SupermarketScraper()
    supermarkets = scraper.scrape_supermarkets()
    for market in supermarkets:
        product_repo.insert_supermarket(market["name"], market["location"])

    # Daten speichern
    for item in results:
        use_case.execute(item)

    print("Done!")

def main():
    scheduler = SchedulerService()

    # Job hinzufügen
    scheduler.add_job(1, run_service)

    # Initiale Ausführung
    run_service()

    # Scheduler starten
    scheduler.run()

if __name__ == "__main__":
    main()
