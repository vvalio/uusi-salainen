import { useReducer, type ActionDispatch } from 'react';

/**
 * Simple hook that toggles a boolean on and off.
 *
 * @param initialState the initial state of the toggle
 * @returns the new, flipped state of the toggle
 */
const useToggle = (initialState: boolean): [boolean, ActionDispatch<[]>] =>
  useReducer(x => !x, initialState);

export default useToggle;
