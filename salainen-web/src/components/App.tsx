import { Box, Button, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { useAuth } from 'react-oidc-context';
import { BrowserRouter, Route, Routes } from 'react-router';
import LoadingBackdrop from './LoadingBackdrop';
import { safeDecode, type DecodedJwt } from '../jwt/jwt';
import AccessController from './auth/AccessController';
import Home from './admin/Home';

/**
 * Application root component that renders all other components
 */
const App: React.FC = () => {
  const auth = useAuth();

  const [decodedJwt, setDecodedJwt] = useState<DecodedJwt | undefined>(
    undefined
  );

  useEffect(() => {
    if (auth.user) {
      setDecodedJwt(safeDecode(auth.user.access_token));
    }
  }, [auth]);

  const handleButtonClick = () => {
    const redirectUrl = 'http://localhost:5000';
    if (auth.isAuthenticated) {
      auth.signoutRedirect({ post_logout_redirect_uri: redirectUrl });
    } else {
      auth.signinRedirect({ redirect_uri: redirectUrl });
    }
  };

  return (
    <Box>
      <BrowserRouter>
        <Routes>
          <Route
            path="/admin/home"
            element={
              <AccessController
                needAuth
                roles={['salainen-admin']}
                fallback={<h1>NOT HERE</h1>}
              >
                <Home />
              </AccessController>
            }
          />
          <Route
            path="/"
            element={
              <>
                <LoadingBackdrop open={auth.isLoading} />
                <Box>
                  <Typography variant="h6">
                    Logged in: {auth.isAuthenticated ? 'true' : 'false'}
                  </Typography>

                  {decodedJwt && auth.isAuthenticated && (
                    <Box>
                      <Typography variant="body1">
                        Username: {decodedJwt.preferred_username}
                      </Typography>

                      <Typography variant="body1">
                        Roles:{' '}
                        {(decodedJwt.realm_access.roles as string[]).join(' ')}
                      </Typography>
                    </Box>
                  )}

                  <Button variant="contained" onClick={handleButtonClick}>
                    {auth.isAuthenticated ? 'Log out' : 'Log in'}
                  </Button>
                </Box>
              </>
            }
          />
        </Routes>
      </BrowserRouter>
    </Box>
  );
};

export default App;
