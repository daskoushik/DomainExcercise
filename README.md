=============================================================Customers=======================================================================

----To get the list of theatres existing in the system---GET call
http://localhost:8080/audience/theatres/

----To get the list of theatres existing in the given city---GET call
http://localhost:8080/audience/theatres/city/mumbai

----To get the list of offers in the given theatre---GET call
http://localhost:8080/audience/theatres/theatre/MarvelTheatres/mumbai/400001/offer


----To get the list of theatres showing the given show in the given city---GET call
http://localhost:8080/audience/theatres/city/mumbai/show/RRR

----To get the list of theatres showing the given show in the given city for the given time---POST call
http://localhost:8080/audience/theatres/city/mumbai/show/RRR
RequestBody Template :
{
   "date": "16/4/2022",
   "hour": "10",
   "min": "30"
}


----To get the dummy booking template to use in the system---GET call
http://localhost:8080/audience/theatres/booking/dummy



----To book the tickets i.e. confirm the booking in the system---POST call
http://localhost:8080/audience/theatres/booking/
RequestBody Template:
{
    "tktsCount": 3,
    "seatsLoc": {
        "1": [
            2,
            3,
            4
        ]
    },
    "bookingTime": "2022-04-16T10:01:19.5023194",
    "showTime": "2022-04-17T10:30:19.5023194",
    "city": "Mumbai",
    "pinCode": "400001",
    "theatre": "MarvelTheatres",
    "show": "RRR"
}



----To get the booking details based on the inSystemBookingID---GET call
http://localhost:8080/audience/theatres/booking/a-1Marv31811498199531194100





















=============================================================Theatre Owners=======================================================================

----To get all the screen details of the given theatre---GET call
http://localhost:8080/theatreOwner/MarvelTheatres/mumbai/400001/screens


----To get all the show details running in the given theatre---GET call
http://localhost:8080/theatreOwner/MarvelTheatres/mumbai/400001/shows


----To add a new show to the given theatre---POST call
http://localhost:8080/theatreOwner/MarvelTheatres/mumbai/400001/newShow
RequestBody Template :
{
                "showName": "RRR",
                "type": "MOVIE",
                "startTime": "2022-04-17T18:45:00",
                "durationInMinutes": 182,
                "showPrice": 300,
                "screen": "BMS16501063478651",
                "offer": []
}



----To update the existing show for the given theatre---PUT call
http://localhost:8080/theatreOwner/MarvelTheatres/mumbai/400001/updateShow
RequestBody Template :
{
                "showID": "MarvelTheatresR81426MOVIE",
                "showName": "RRR",
                "type": "MOVIE",
                "startTime": "2022-04-17T10:15:00",
                "durationInMinutes": 182,
                "showPrice": 300,
                "screen": "BMS16501063478651",
                "offer": []
}


----To delete the existing show in the given theatre---DELETE call
http://localhost:8080/theatreOwner/MarvelTheatres/mumbai/400001/deleteShow/MarvelTheatresR81426MOVIE1650123569333
