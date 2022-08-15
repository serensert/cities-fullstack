import { Component, Input, OnInit } from '@angular/core';
import { CityService } from 'src/app/services/city.service';
import { ActivatedRoute, Router } from '@angular/router';
import { City } from 'src/app/models/city.model';

@Component({
  selector: 'app-city-details',
  templateUrl: './city-details.component.html',
  styleUrls: ['./city-details.component.css']
})
export class CityDetailsComponent implements OnInit {

  @Input() viewMode = false;

  @Input() currentCity: City = {
    name: '',
    version: 1,
    extension: ''
  };
  
  message = '';

  constructor(
    private cityService: CityService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    if (!this.viewMode) {
      this.message = '';
      this.getCity(this.route.snapshot.params["id"]);
    }
  }

  getCity(id: string): void {
    this.cityService.get(id)
      .subscribe({
        next: (data) => {
          this.currentCity = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  updateCity(): void {
    this.message = '';

    this.cityService.update(this.currentCity.id, this.currentCity)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.message = res.message ? res.message : 'This city was updated successfully!';
        },
        error: (e) => console.error(e)
      });
  }

}