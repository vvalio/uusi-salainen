// JWT helper functions
import { jwtDecode } from 'jwt-decode';
import type { JwtClaims } from 'oidc-client-ts';

export type DecodedJwt = JwtClaims & {
  realm_access: {
    roles: string[];
  };

  resource_access: {
    [clientId: string]: {
      roles: string[];
    };
  };

  // Split in processing; originally a space-separated string
  scope: string[];
  // only available when 'email' in scope
  email_verified?: boolean;
  // 'profile' in scope
  name?: string;
  // 'profile' in scope
  preferred_username?: string;
  // 'profile' in scope
  given_name?: string;
  // 'profile' in scope
  family_name?: string;
  // 'email' in scope
  email?: string;
};

/**
 * Parses the passed JWT, returns undefined if it is undefined. Will crash and burn if the JWT is malformed, however.
 * @param jwt The JWT to parse
 * @returns parsed JWT
 */
export const safeDecode = (jwt: string | undefined): DecodedJwt | undefined => {
  if (!jwt) {
    return undefined;
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const decoded = jwtDecode(jwt) as any; // can't use DecodedJwt because scope is still a string
  decoded['scope'] = (decoded['scope'] as string).split(' ');

  return decoded as DecodedJwt;
};

/**
 * Parses the JWT and checks if it has the given roles.
 * @param jwt the jwt or undefined, in which case this function simply returns false
 * @param roles the roles to check for, in realm_access. Use hasClientRoles(string, string, ...string) if wanting to check for client roles
 */
export const hasRoles = (
  jwt: string | undefined,
  ...roles: string[]
): boolean => {
  const decoded = safeDecode(jwt);
  if (!decoded) {
    return false;
  }

  return roles.every(role => decoded.realm_access.roles.includes(role));
};

/**
 * Checks if the JWT has the given client roles for the given client
 * @param jwt The jwt or undefined
 * @param clientId the client id
 * @param roles the roles to check for
 */
export const hasClientRoles = (
  jwt: string | undefined,
  clientId: string,
  ...roles: string[]
): boolean => {
  const decoded = safeDecode(jwt);
  if (!decoded) {
    return false;
  }

  return roles.every(role =>
    decoded.resource_access[clientId].roles.includes(role)
  );
};
