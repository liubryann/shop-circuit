import numpy as np
import pandas as pd
from sqlalchemy import create_engine


if __name__ == "__main__":
    dataset = pd.read_csv("generic-food.csv")
    dataset.drop(labels=["SCIENTIFIC NAME","SUB GROUP"],inplace=True,axis=1)

    engine = create_engine('sqlite:///./foodtoshelf.db',echo=False)
    dataset.to_csv(r"./new.csv")
    dataset.to_sql("foodtoshelf",con=engine)