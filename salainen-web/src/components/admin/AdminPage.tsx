import AccountCircleOutlined from '@mui/icons-material/AccountCircleOutlined';
import MenuIcon from '@mui/icons-material/Menu';
import HomeIcon from '@mui/icons-material/Home';
import AppBar, { type AppBarProps } from '@mui/material/AppBar';
import Box, { type BoxProps } from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import IconButton from '@mui/material/IconButton';
import List from '@mui/material/List';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import useToggle from '../../hooks/useToggle';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import DarkModeIcon from '@mui/icons-material/DarkMode';
import LightModeIcon from '@mui/icons-material/LightMode';
import LinkIcon from '@mui/icons-material/Link';
import ListItemText from '@mui/material/ListItemText';
import { styled, useColorScheme } from '@mui/material/styles';
import ChevronLeft from '@mui/icons-material/ChevronLeft';
import Divider from '@mui/material/Divider';
import ListItemButton from '@mui/material/ListItemButton';
import React, { useEffect, useState, type ReactNode } from 'react';
import { useCookies } from 'react-cookie';
import { useTranslation } from 'react-i18next';
import { useLocation, useNavigate } from 'react-router';
import Switch from '@mui/material/Switch';
import Links from './Links';

const drawerWidth = '20%';
const drawerCookieName = 'salainen_admin_panel_drawer_open';
const drawerCookiePath = '/admin';

// Below mostly copied from MUI docs

/**
 * The part before the drawer items, spanning the height of the app bar
 */
const DrawerHeader = styled('div')(({ theme }) => ({
  display: 'flex',
  alignItems: 'center',
  padding: theme.spacing(0, 1),
  ...theme.mixins.toolbar,
  justifyContent: 'flex-end',
}));

/**
 * Makes the app bar shrink and flex in the same speed as the drawer when the drawer is opened or closed
 */
const AnimatedAppBar = styled(AppBar, {
  shouldForwardProp: prop => prop !== 'drawerOpen',
})<AppBarProps & { drawerOpen: boolean }>(({ theme }) => ({
  transition: theme.transitions.create(['margin', 'width'], {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  variants: [
    {
      props: ({ drawerOpen }) => drawerOpen,
      style: {
        width: `calc(100% - ${drawerWidth})`,
        marginLeft: drawerWidth,
        transition: theme.transitions.create(['margin', 'width'], {
          easing: theme.transitions.easing.easeOut,
          duration: theme.transitions.duration.enteringScreen,
        }),
      },
    },
  ],
}));

/**
 * Makes the content shrink and flex in the same speed as the drawer when the drawer is opened or closed
 */
const AnimatedContent = styled(Box, {
  shouldForwardProp: prop => prop !== 'drawerOpen',
})<BoxProps & { drawerOpen: boolean }>(({ theme }) => ({
  transition: theme.transitions.create(['margin', 'width'], {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),

  variants: [
    {
      props: ({ drawerOpen }) => drawerOpen,
      style: {
        width: `calc(100% - ${drawerWidth})`,
        marginLeft: drawerWidth,
        transition: theme.transitions.create(['margin', 'width'], {
          easing: theme.transitions.easing.easeOut,
          duration: theme.transitions.duration.enteringScreen,
        }),
      },
    },
  ],
}));

type NavigationItemProps = {
  path: string;
  label: string;
  icon: ReactNode;
};

/**
 * Component to render a specialized ListItem that hightlights itself when its own route is selected
 */
const NavigationItem: React.FC<NavigationItemProps> = ({
  path,
  label,
  icon,
}) => {
  const { t } = useTranslation();
  const location = useLocation();
  const navigate = useNavigate();

  const isSelected = location.pathname === path;

  return (
    <ListItem disablePadding>
      <ListItemButton onClick={() => navigate(path)}>
        <ListItemIcon>{icon}</ListItemIcon>
        <ListItemText
          primary={t(label)}
          slotProps={{
            primary: {
              sx: { textDecoration: isSelected ? 'underline' : 'none' },
              fontWeight: isSelected ? 'bolder' : 'auto',
            },
          }}
        />
      </ListItemButton>
    </ListItem>
  );
};

const navigationTargets = [
  {
    path: '/admin',
    label: 'Home',
    icon: <HomeIcon />,
    elementFn: () => <></>,
  },
  {
    path: '/admin/links',
    label: 'Links',
    icon: <LinkIcon />,
    elementFn: () => <Links />,
  },
];

const componentMap = Object.fromEntries(
  navigationTargets.map(target => [target.path, target.elementFn])
);

/**
 * Renders the correct element for the route
 */
const AdminContent: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [renderedElement, setRenderedElement] = useState<ReactNode | undefined>(
    undefined
  );

  useEffect(() => {
    if (Object.hasOwn(componentMap, location.pathname)) {
      // @ts-ignore 7053 hasOwn check
      setRenderedElement(componentMap[location.pathname]());
    } else {
      setRenderedElement(undefined);
      navigate('/admin');
    }
  }, [location.pathname]);

  return (
    <Box display="flex" justifyContent="center">
      {renderedElement}
    </Box>
  );
};

/**
 * Pulls all of the above together to create an actual webpage
 */
const AdminPage: React.FC = () => {
  const [cookies, setCookie, _] = useCookies([drawerCookieName]);
  const [drawerOpen, toggleDrawerOpen] = useToggle(
    !!cookies.salainen_admin_panel_drawer_open
  );

  const { mode: colorMode, setMode: setColorMode } = useColorScheme();

  useEffect(() => {
    setCookie(drawerCookieName, drawerOpen, { path: drawerCookiePath });
  }, [drawerOpen]);

  return (
    <>
      <AnimatedAppBar drawerOpen={drawerOpen} color="info" position="fixed">
        <Toolbar sx={{ justifyContent: 'space-between' }}>
          <Box display="flex" flexDirection="row" alignItems="center">
            <IconButton
              size="large"
              edge="start"
              color="inherit"
              aria-label="menu"
              onClick={toggleDrawerOpen}
            >
              <MenuIcon />
            </IconButton>
            <Typography variant="h5" component="div" marginLeft={2}>
              Admin console
            </Typography>
          </Box>
          <Box>
            <Switch
              icon={<DarkModeIcon />}
              checkedIcon={<LightModeIcon />}
              onChange={ev =>
                setColorMode(ev.target.checked ? 'dark' : 'light')
              }
              checked={colorMode === 'dark'}
            />
            <IconButton edge="end" sx={{ flexGrow: 0 }}>
              <AccountCircleOutlined sx={{ color: 'white' }} />
            </IconButton>
          </Box>
        </Toolbar>
      </AnimatedAppBar>
      <Drawer
        open={drawerOpen}
        anchor="left"
        variant="persistent"
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          '& .MuiDrawer-paper': {
            width: drawerWidth,
            boxSizing: 'border-box',
            backgroundColor: 'primary',
          },
        }}
      >
        <DrawerHeader>
          <IconButton onClick={toggleDrawerOpen}>
            <ChevronLeft />
          </IconButton>
        </DrawerHeader>
        <Divider />
        <List>
          {navigationTargets.map(props => (
            <NavigationItem {...props} key={props.path} />
          ))}
        </List>
      </Drawer>
      <AnimatedContent mt={10} drawerOpen={drawerOpen}>
        <AdminContent />
      </AnimatedContent>
    </>
  );
};

export default AdminPage;
