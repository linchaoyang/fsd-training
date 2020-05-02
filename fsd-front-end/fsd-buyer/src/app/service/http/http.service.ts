import { LogService } from './../logger/log.service';
import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap, catchError, map } from 'rxjs/operators';

type HttpOptions = {
  headers?: HttpHeaders;
  params?: HttpParams;
  responseType?: 'json';
};

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(
    private http: HttpClient,
    private logger: LogService) { }

  /** GET Items from the server */
  getItems<T>(url: string, options?: HttpOptions): Observable<T[]> {
    return this.http.get<T[]>(url, options
      )
      .pipe(
        tap(_ => this.logger.log('fetched items')),
        catchError(this.handleError<T[]>('getItems', []))
      );
  }

  /** GET item by id. Will 404 if id not fouTnd */
  getItem<T>(url: string, id: number, options?: HttpOptions): Observable<T> {
    return this.http.get<T>(`${url}/${id}`, options).pipe(
      tap(_ => this.log(`fetched item id=${id}`)),
      catchError(this.handleError<T>(`getItem id=${id}`))
    );
  }

  /* GET items whose name contains search term */
  searchItems<T>(url: string, term: string, options?: HttpOptions): Observable<T[]> {
    if (!term.trim()) {
      // if not search term, return empty item array.
      return of([]);
    }
    return this.http.get<T[]>(`${url}/?name=${term}`, options).pipe(
      tap(x => x.length ?
        this.log(`found items matching "${term}"`) :
        this.log(`no items matching "${term}"`)),
      catchError(this.handleError<T[]>('searchItems', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new item to the server */
  addItem<T>(url: string, item: T, options?: HttpOptions): Observable<T> {
    return this.http.post<T>(url, item, options).pipe(
      tap((newItem: T) => this.log(`added item w/ id=${(newItem as unknown as Item).id}`)),
      catchError(this.handleError<T>('addItem'))
    );
  }

  /** DELETE: delete the item from the server */
  deleteItem<T>(url: string, item: T | number, options?: HttpOptions): Observable<T> {
    const id = typeof item === 'number' ? item : (item as unknown as Item).id;

    return this.http.delete<T>(`${url}/${id}`, options).pipe(
      tap(_ => this.log(`deleted item id=${id}`)),
      catchError(this.handleError<T>('deleteItem'))
    );
  }

  /** PUT: update the item on the server */
  updateItem<T>(url: string, item: T, options?: HttpOptions): Observable<any> {
    return this.http.put(url, item, options).pipe(
      tap(_ => this.log(`updated item id=${(item as unknown as Item).id}`)),
      catchError(this.handleError<T>('updateItem'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<V>(operation = 'operation', result?: V) {
    return (error: any): Observable<V> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result);
    };
  }

  /** Log a HttpService message with the LogService */
  private log(message: string) {
    this.logger.log(`LogService: ${message}`);
  }
}

export declare interface Item {
  id: number;
}
