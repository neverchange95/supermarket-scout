import schedule
import time

class SchedulerService:
    def __init__(self):
        self.scheduler = schedule

    def add_job(self, interval, job_func, *args, **kwargs):
        """
        Fügt einen neuen Job zum Scheduler hinzu.
        :param interval: Intervall in Stunden.
        :param job_func: Funktion, die ausgeführt werden soll.
        """
        self.scheduler.every(interval).hours.do(job_func, *args, **kwargs)

    def run(self):
        """
        Startet den Scheduler.
        """
        print("Scheduler läuft...")
        while True:
            self.scheduler.run_pending()
            time.sleep(1)
