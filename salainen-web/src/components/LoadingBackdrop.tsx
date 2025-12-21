import Backdrop from '@mui/material/Backdrop';
import Grid from '@mui/material/Grid';
import CircularProgress from '@mui/material/CircularProgress';
import Typography from '@mui/material/Typography';
import { t } from 'i18next';

/**
 * Renders a backdrop with a circular progress indicator, if open is true. Otherwise renders nothing.
 * @param open whether the backdrop is displayed
 */
const LoadingBackdrop: React.FC<{ open: boolean }> = ({ open }) => {
  return (
    <Backdrop open={open}>
      <Grid container direction="column">
        <Grid>
          <CircularProgress />
        </Grid>
        <Grid>
          <Typography variant="caption">{t('Loading')}</Typography>
        </Grid>
      </Grid>
    </Backdrop>
  );
};

export default LoadingBackdrop;
