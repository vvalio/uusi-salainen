import Warning from '@mui/icons-material/Warning';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { useTranslation } from 'react-i18next';
import { useAuth } from 'react-oidc-context';
import { useNavigate } from 'react-router';

const AccessDenied: React.FC = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const auth = useAuth();

  const handleGoHome = () => {
    navigate('/');
  };

  const handleLogOut = () => {
    auth.signoutRedirect({ post_logout_redirect_uri: 'http://localhost:5000' });
  };

  return (
    <Box
      display="flex"
      flexDirection="column"
      justifyContent="center"
      alignItems="center"
      minHeight="100vh"
      fontSize="100px"
    >
      <Warning color="warning" fontSize="inherit" />
      <Typography variant="h5">
        {t('You do not have access to view this page.')}
      </Typography>
      <Box display="flex" flexDirection="row" mt={4}>
        <Button
          variant="outlined"
          sx={{ marginRight: 2 }}
          onClick={handleGoHome}
        >
          {t('Go home')}
        </Button>
        <Button variant="contained" color="error" onClick={handleLogOut}>
          {t('Log out')}
        </Button>
      </Box>
    </Box>
  );
};

export default AccessDenied;
