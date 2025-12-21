import Backdrop from '@mui/material/Backdrop';
import CircularProgress from '@mui/material/CircularProgress';
import Typography from '@mui/material/Typography';
import { useEffect, useState } from 'react';
import { useAuth } from 'react-oidc-context';
import { useNavigate } from 'react-router';
import { safeDecode } from '../../jwt/jwt';

const Callback: React.FC = () => {
  const navigate = useNavigate();
  const auth = useAuth();

  // undefined: token not retrieved yet
  const [accessDenied, setAccessDenied] = useState<boolean | undefined>(
    undefined
  );

  useEffect(() => {
    if (!auth.isLoading && auth.isAuthenticated) {
      const decoded = safeDecode(auth.user?.access_token);
      if (!decoded) {
        return;
      }

      setAccessDenied(!decoded.realm_access.roles.includes('salainen-admin'));
    }
  }, [auth.isLoading, auth.isAuthenticated]);

  useEffect(() => {
    if (accessDenied === false) {
      navigate('/admin/home');
    } else if (accessDenied) {
      navigate('/access-denied');
    }
  }, [accessDenied]);

  return (
    <div>
      <Backdrop open={auth.isLoading}>
        <CircularProgress />
        <Typography variant="h6">Redirecting...</Typography>
      </Backdrop>
    </div>
  );
};

export default Callback;
