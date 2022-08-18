import { Component, OnInit } from '@angular/core';
import { City } from 'src/app/models/city.model';
import { CityService } from 'src/app/services/city.service';

@Component({
  selector: 'app-cities-list',
  templateUrl: './cities-list.component.html',
  styleUrls: ['./cities-list.component.css']
})
export class CitiesListComponent implements OnInit {

  cities: City[] = [];
  currentCity: City = {};
  currentIndex = -1;
  page = 1;
  count = 0;
  pageSize = 5;
  pageSizes = [5, 10, 20, 50, 100];
  name = '';
  extension = '';

  constructor(private cityService: CityService) { }

  ngOnInit(): void {
    this.retrieveCities();
  }

  getRequestParams(searchName: string, page: number, pageSize: number): any {
    let params: any = {};
    if (searchName) {
      params[`name`] = searchName;
    }
    if (page) {
      params[`page`] = page - 1;
    }
    if (pageSize) {
      params[`size`] = pageSize;
    }
    return params;
  }

  retrieveCities(): void {
    const params = this.getRequestParams(this.name, this.page, this.pageSize);
    this.cityService.getAll(params)
      .subscribe(response => {
        const { content, totalElements } = response;
        this.cities = content;
        this.count = totalElements;
        console.log(response);
      },
      error => {
        console.log(error);
      });
  }
  handlePageChange(event: number): void {
    this.page = event;
    this.retrieveCities();
  }
  handlePageSizeChange(event: any): void {
    this.pageSize = event.target.value;
    this.page = 1;
    this.retrieveCities();
  }
  refreshList(): void {
    this.retrieveCities();
    this.currentCity = {};
    this.currentIndex = -1;
  }

  setActiveCity(city: City, index: number): void {
    this.currentCity = city;
    this.currentIndex = index;
  }

  searchName(): void {
    this.page = 1;
    this.retrieveCities();
  }

}