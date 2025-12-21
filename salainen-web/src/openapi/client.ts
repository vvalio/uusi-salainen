import type { Key } from 'react';
import type { paths, components } from './salainen-api';
import createClient, { type Middleware } from 'openapi-fetch';

export const apiClient = createClient<paths>({
  baseUrl: 'http://localhost:9000',
});

const middleware: Middleware = {
  onRequest: async ({ request }) => {
    const accessToken = JSON.parse(
      sessionStorage.getItem(
        'oidc.user:http://localhost:8080/realms/salainen:salainen_app'
      )!
    )?.access_token;

    if (!accessToken) {
      return undefined;
    }

    if (accessToken) {
      request.headers.set('Authorization', `Bearer ${accessToken}`);
      return request;
    }
  },
};

apiClient.use(middleware);

export namespace types {
  export type RegistrationLink = components['schemas']['RegistrationLinkDTO'];
  export type Christmas = components['schemas']['ChristmasDTO'];

  export type ApiResult<T> = {
    data?: T;
    error?: ApiError;
    responseStatus?: number;
    isPending: boolean;
    isError: boolean;
  };

  export type ApiError = {
    timestamp: Date;
    status: number;
    error: string;
    message: string;
    path: string;
  };

  export type DataWithKey<T> = T & {
    key: Key;
  };
}
