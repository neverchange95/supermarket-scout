import json
from src.core.ports.category_whitelist_config import CategoryConfig

class CategoryConfig(CategoryConfig):
    def __init__(self, config_file):
        self.config_file = config_file

    def get_allowed_categories(self):
        with open(self.config_file, "r") as file:
            config = json.load(file)
            return config.get("allowed_categories", [])
