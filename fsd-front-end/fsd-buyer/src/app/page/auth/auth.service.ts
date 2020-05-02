import { Api } from './../../../../config/api';
import { Observable, of } from 'rxjs';
import { tap, delay, map } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // store the URL so we can redirect after logging in
  redirectUrl: string;

  constructor(private http: HttpClient, private router: Router) { }

  isLoggedIn(): boolean {
    // TODO check token valid
    return sessionStorage.getItem('loggedUser') != null;
  }

  getUser(): User {
    return JSON.parse(sessionStorage.getItem('loggedUser'));
  }

  login(username: string, password: string): Observable<boolean> {

    return this.http.post<User>(Api.auth_url, {username, password}, Api.httpOptions).pipe(
      map(user => {
        sessionStorage.setItem('loggedUser', JSON.stringify(user));
        return true;
      })
    );
  }
}
