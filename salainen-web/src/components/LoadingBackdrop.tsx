import { Backdrop, Grid, CircularProgress, Typography } from '@mui/material';
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
