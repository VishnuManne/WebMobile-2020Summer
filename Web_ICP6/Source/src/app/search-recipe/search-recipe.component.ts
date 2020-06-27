import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from "./../../environments/environment";

@Component({
  selector: 'app-search-recipe',
  templateUrl: './search-recipe.component.html',
  styleUrls: ['./search-recipe.component.css']
})
export class SearchRecipeComponent implements OnInit {
  private Eda_id = environment.edamam_id;
  private Eda_key = environment.edamam_key;
  private Foursq_id = environment.foursquare_id;
  private Foursq_key = environment.foursquare_key;
  private recipeurl = `https://api.edamam.com/search?app_id=${this.Eda_id}&app_key=${this.Eda_key}`;
  private venueurl = `https://api.foursquare.com/v2/venues/search?client_id=${this.Foursq_id}&client_secret=${this.Foursq_key}&v=20180323`;
  @ViewChild('recipe') recipes: ElementRef;
  @ViewChild('place') places: ElementRef;
  recipe_val: any;
  place_val: any;
  venue_list = [];
  recipe_list = [];
  current_Lat: any;
  current_Long: any;
  geolocationPosition: any;

  constructor(private _http: HttpClient) {
  }

  ngOnInit() {

    window.navigator.geolocation.getCurrentPosition(
      position => {
        this.geolocationPosition = position;
        this.current_Lat = position.coords.latitude;
        this.current_Long = position.coords.longitude;
      });
  }

  getVenues() {

    this.recipe_val = this.recipes.nativeElement.value;
    this.place_val = this.places.nativeElement.value;

    if (this.recipe_val !== null) {
      /*
       * code to get recipe
       */
      this.recipe_list = [];
      let food_url = this.recipeurl + '&q='+ this.recipe_val;
      this._http.get(food_url).subscribe(resp => {
        let recipes = resp["hits"];
        recipes.map(ele => {
          let recipe = ele['recipe'];
            const recobj = {
              name : ele .recipe.label,
              url:ele.recipe.url,
              icon: ele.recipe.image
            }
            this.recipe_list.push(recobj);

          })
      });

    }

    if (this.place_val != null && this.place_val !== '' && this.recipe_val != null && this.recipe_val !== '') {
      /*
       * code to get place
       */
      this.venue_list = [];
      let place_url = this.venueurl + "&query=" + this.recipe_val + '&near='+ this.place_val;
      this._http.get(place_url).subscribe(resp => {
        let venues = resp['response']['venues'];
        venues.map(ele => {

          const venobj = {
            name: ele.name,
            location : {
              formattedAddress: [ele.location.address,ele.location.city,ele.location.country]
            }
          }
          this.venue_list.push(venobj);
        })
      })
    }
  }
}
