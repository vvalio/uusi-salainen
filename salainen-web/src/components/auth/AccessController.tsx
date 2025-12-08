import { type PropsWithChildren, type ReactNode } from 'react';
import { useAuth } from 'react-oidc-context';
import LoadingBackdrop from '../LoadingBackdrop';
import { hasRoles } from '../../jwt/jwt';

export type AccessControllerProps = PropsWithChildren<{
  needAuth: boolean;
  roles?: string[];

  /**
   * Fallback to render if condition fails, or function to execute
   */
  fallback?: ReactNode | (() => void);
}>;

/**
 * Restricts access to child components based on the user's authentication state or roles.
 */
const AccessController: React.FC<AccessControllerProps> = ({
  needAuth,
  roles,
  children,
  fallback,
}) => {
  const auth = useAuth();

  const allowed = roles
    ? hasRoles(auth.user?.access_token, ...roles)
    : !needAuth || auth.isAuthenticated;

  if (!auth.isLoading && !allowed && typeof fallback === 'function') {
    fallback();
  }

  // Render an undefined if fallback is not renderable
  const fallbackComponent =
    typeof fallback === 'function' ? undefined : fallback;

  return (
    <>
      <LoadingBackdrop open={auth.isLoading} />
      {allowed ? children : fallbackComponent}
    </>
  );
};

export default AccessController;
